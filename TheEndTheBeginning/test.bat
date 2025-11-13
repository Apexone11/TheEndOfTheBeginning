@echo off
echo Test
if not exist "mvnw.cmd" (
    echo ERROR: Maven Wrapper not found!
) else (
    echo Maven wrapper found
)
echo Done