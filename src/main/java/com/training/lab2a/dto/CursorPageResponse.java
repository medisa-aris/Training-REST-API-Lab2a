package com.training.lab2a.dto;

import java.util.List;

public record CursorPageResponse<T>(List<T> items, Long nextCursor, boolean hasMore) {
}
