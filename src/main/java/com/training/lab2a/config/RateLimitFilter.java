package com.training.lab2a.config;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bandwidth;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class RateLimitFilter extends OncePerRequestFilter {
  private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

  private Bucket newBucket(int perMinute) {
    return Bucket.builder()
      .addLimit(Bandwidth.simple(perMinute, Duration.ofMinutes(1)))
      .build();
  }

  private int tierFor(Authentication auth) {
    if (auth == null || auth instanceof AnonymousAuthenticationToken) return 10;
    if (auth.getAuthorities().stream().anyMatch(a ->
        a.getAuthority().equals("ROLE_PREMIUM"))) return 1000;    // premium
    return 100;                                                    // authenticated
  }

  @Override protected void doFilterInternal(HttpServletRequest req,
      HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    var key = (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken))
      ? auth.getName() : req.getRemoteAddr();      // per-principal, not just IP
    var bucket = buckets.computeIfAbsent(key, k -> newBucket(tierFor(auth)));
    var probe = bucket.tryConsumeAndReturnRemaining(1);
    res.setHeader("X-RateLimit-Remaining", String.valueOf(probe.getRemainingTokens()));
    if (probe.isConsumed()) {
      chain.doFilter(req, res);
    } else {
      res.setStatus(429);
      res.setHeader("Retry-After", String.valueOf(
        probe.getNanosToWaitForRefill() / 1_000_000_000));
      res.setContentType("application/problem+json");
      res.getWriter().write("{\"title\":\"Too Many Requests\",\"status\":429}");
    }
  }
}
