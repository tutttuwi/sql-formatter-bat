@echo off
setlocal
echo ����������������������������������������
echo ���@
echo ���@sql-formatter-bat�����@�J�n
echo ���@
echo ����������������������������������������

rem �J�����g�f�B���N�g��
set CUR_DIR=%~dp0

rem �����`�F�b�N
if "%1" EQU "" (
  echo ---------------------------------------------------------------
  echo.
  echo �f�[�^�i�[�f�B���N�g�����w�肳��Ă��܂���B�������I�����܂��B
  echo.
  echo ---------------------------------------------------------------
  pause
  exit
) else (
  set targetFile=%1
)
rem ���̓t�@�C�������G���R�[�f�B���O
set inputFileEncode=UTF-8
rem SQL Server
set dialect=Tsql

rem JAVA_HOME�ݒ�
set PATH=C:\java\openjdk-11.0.2_windows-x64_bin\jdk-11.0.2\bin;%PATH%

rem ==============================
rem ���������w�����
rem ------------------------------
rem targetFile            :SqlFormat�Ώۃt�@�C���t���p�X
rem inputFileEncode       :�t�@�C���G���R�[�h�i�f�t�H���gUTF-8�j
rem ==============================
java -cp %CUR_DIR%\resources\prop;%CUR_DIR%\resources\lib\sqlformatterbat-0.0.1-SNAPSHOT-all.jar ^
  org.springframework.batch.core.launch.support.CommandLineJobRunner ^
  sqlformatterbat.job0010.AppConfig0010 sqlFormatterBatJob ^
  targetFile=%targetFile% ^
  inputFileEncode=%inputFileEncode% ^
  dialect=%dialect%


echo ����������������������������������������
echo ���@
echo ���@sql-formatter-bat�@�I��
echo ���@
echo ����������������������������������������

exit

