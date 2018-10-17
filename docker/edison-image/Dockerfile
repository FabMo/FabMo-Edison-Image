FROM edison/download

WORKDIR /home/edison/fabmo-edison-image/edison-src/out/current/poky

RUN source ./oe-init-build-env ../build  && cd ../build; make release release-windows
WORKDIR /home/edison
