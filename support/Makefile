all: release release-windows

image: build
	../../../FabMo-Edison-Image/utils/postBuild.sh .
mount: image
	-sudo umount /mnt/loop
	sudo mount -o rw,loop toFlash/edison-image-edison.ext4 /mnt/loop
build:
	bitbake -c fetch -f fabmo-engine fabmo-updater
	bitbake -c unpack -f fabmo-engine fabmo-updater
	bitbake fabmo-image u-boot
release: image
	zip -r fabmo-image-latest.zip toFlash

release-windows:
	rm -f fabmo-image-latest-windows.zip 
	cd toFlash; zip -ry ../fabmo-image-latest-windows.zip *
	cd build-tools/dfu-util-x86; zip -ruy ../../fabmo-image-latest-windows.zip *
	cd build-tools; zip -ruy ../fabmo-image-latest-windows.zip flash.bat

flash:
	cd toFlash \
	./flashall.sh
clean:
	bitbake -c cleansstate -f fabmo-engine fabmo-updater
	rm -rf toFlash fabmo-image-latest.zip fabmo-image-latest-windows.zip

.PHONY: clean
