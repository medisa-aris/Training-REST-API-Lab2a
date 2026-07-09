$ErrorActionPreference = 'Stop'

$repoRoot = Split-Path -Parent $PSScriptRoot
$process = Start-Process -FilePath "$repoRoot\mvnw.cmd" -ArgumentList 'spring-boot:run -DskipTests' -WorkingDirectory $repoRoot -PassThru -WindowStyle Hidden

try {
    $started = $false
    for ($i = 0; $i -lt 60; $i++) {
        try {
            Invoke-WebRequest -Uri 'http://localhost:8080/v3/api-docs' -UseBasicParsing | Out-Null
            $started = $true
            break
        }
        catch {
            Start-Sleep -Seconds 2
        }
    }

    if (-not $started) {
        throw 'Spring Boot did not start in time for Newman tests.'
    }

    npm --prefix $PSScriptRoot run test:newman
}
finally {
    if (-not $process.HasExited) {
        Stop-Process -Id $process.Id -Force
    }
}