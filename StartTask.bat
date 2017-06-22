@echo off
set /p id="Enter Task: "
D:
cd D:\Neon\AutomateWorkProcess
java -cp D:\Neon\AutomateWorkProcess\lib\*;D:\Neon\AutomateWorkProcess\bin -Dtask=%id% org.testng.TestNG testng.xml
D:
cd D:\Work\%id%
start .
timeout 10
