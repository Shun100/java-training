#!/bin/bash

# コンテナの停止
docker stop java-training > /dev/null 2>&1

# コンテナの削除
docker rm java-training > /dev/null 2>&1