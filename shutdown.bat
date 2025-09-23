@echo off

@REM コンテナの停止
docker stop java-training > nul 2>&1

@REM コンテナの削除
docker rm java-training > nul 2>&1