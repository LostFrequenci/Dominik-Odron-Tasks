call runcrud.bat
if "%ERRORLEVEL%" == "0" goto browserrunner
echo.
echo Program has errors - breaking work
goto fail

:browserrunner
timeout 60
start Chrome http://localhost:8080/crud/v1/task/getTasks
goto end

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished.