echo off

echo "compile -> package -> git add -> commit -> push -> cp to lib folder"

set IXXASCALALIBOK=
set IXXASCALALIBCOMMES=

set /P IXXASCALALIBOK="COMPILE?"
call sbt compile
set /P IXXASCALALIBOK="PACKAGE?"
call sbt package
set /P IXXASCALALIBOK="GIT ADD?"
call git add .

set /P IXXASCALALIBOK="COMMIT?"
set /P IXXASCALALIBCOMMES="commit message : "
call git commit -m %IXXASCALALIBCOMMES%

set /P IXXASCALALIBOK="PUSH?"
call git push origin master

set /P IXXASCALALIBOK="COPY?"
call cp .\target\scala-2.10\ixxascalalib_2.10-0.01.jar %DROPBOX%\Public\liblary

start %HOME%\.sbt\0.13\build.sbt

