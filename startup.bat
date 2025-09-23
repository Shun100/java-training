@echo off

@REM イメージのビルド
docker build -t java-training .

@REM コンテナの中に入る
docker run -it --rm -v %cd%:/work -w /work java-training