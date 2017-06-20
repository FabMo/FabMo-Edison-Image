all: release release-windows

image: build
	../../../FabMo-Edison-Image/utils/postBuild.sh .
mount: image
	sudo umount /mnt/loop
	sudo mount -o ro,loop toFlash/edison-image-edison.ext4 /mnt/loop
build:
	bitbake fabmo-image u-boot
release: image
	zip -r fabmo-image-latest.zip toFlash

release-windows: image
	cd toFlash; zip -ru ../fabmo-image-latest-windows.zip *
	cd build_tools/dfu-util-x86; zip -ru ../../fabmo-image-latest-windows.zip *
	cd build_tools; zip -ru ../fabmo-image-latest-windows.zip flash.bat

flash:
	cd toFlash \
	./flashall.sh
clean:
	bitbake -c cleanall fabmo-engine fabmo-updater
	rm -rf toFlash fabmo-image-latest.zip fabmo-image-latest-windows.zip

.PHONY: clean
