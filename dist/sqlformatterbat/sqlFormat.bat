@echo off
setlocal
echo ■■■■■■■■■■■■■■■■■■■■
echo ■　
echo ■　sql-formatter-bat処理　開始
echo ■　
echo ■■■■■■■■■■■■■■■■■■■■

rem 引数チェック
if "%1" EQU "" (
  echo ---------------------------------------------------------------
  echo.
  echo データ格納ディレクトリが指定されていません。処理を終了します。
  echo.
  echo ---------------------------------------------------------------
  pause
  exit
) else (
  set targetFile=%1
)
rem 入力ファイル文字エンコーディング
set inputFileEncode=UTF-8
rem SQL Server
set dialect=tsql

rem JAVA_HOME設定
rem set PATH=[jdk8- directory you downloaded];%PATH%

rem ==============================
rem ＜説明＞指定引数
rem ------------------------------
rem targetFile            :SqlFormat対象ファイルフルパス
rem inputFileEncode       :ファイルエンコード（デフォルトUTF-8）
rem ==============================
java -cp %CD%\resources\prop;.\resources\lib\sqlformatterbat-0.0.1-SNAPSHOT-all.jar ^
  org.springframework.batch.core.launch.support.CommandLineJobRunner ^
  sqlformatterbat.job0010.AppConfig0010 sqlFormatterBatJob ^
  targetFile=%targetFile% ^
  inputFileEncode=%inputFileEncode% ^
  dialect=%dialect%


echo ■■■■■■■■■■■■■■■■■■■■
echo ■　
echo ■　sql-formatter-bat　終了
echo ■　
echo ■■■■■■■■■■■■■■■■■■■■

pause
rem exit

