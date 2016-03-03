#!/bin/sh

curl -H "Content-Type: application/json" -d "{\"enabled\" : true }" http://localhost:81/network/hotspot/state
