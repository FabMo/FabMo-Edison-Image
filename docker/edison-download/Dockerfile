FROM edison/source

WORKDIR /home/edison/fabmo-edison-image/edison-src/out/current/poky

RUN source ./oe-init-build-env ../build/     && \
    bitbake -c fetchall fabmo-image u-boot

WORKDIR /home/edison
