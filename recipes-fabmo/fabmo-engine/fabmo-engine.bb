DESCRIPTION="The FabMo Engine Service"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${THISDIR}/LICENSE;md5=175792518e4ac015ab6696d16c4f607e"

PV = "1.4.65"

#SRC_URI += "file://latest.js"

DEPENDS = "dbus-glib expat fabmo-updater"
RDEPENDS_${PN} = "git bash nodejs-npm"

do_fetch() {
	cd ${WORKDIR}
	PACKAGE=`node ${THISDIR}/files/latest.js`
	wget ${PACKAGE} -O package.fmp
}

do_unpack() {
	echo "Unpacking"
	cd ${WORKDIR}
    	tar -xvzf package.fmp
	rm -rf ./fabmo-engine-files
	mkdir -p ./fabmo-engine-files
	tar -xvzf files.tar.gz -C ./fabmo-engine-files
	find ./fabmo-engine-files -name '.debug' -print0 | xargs -0 -n 1 rm -rf
}

do_clean() {
	rm -rf ${WORKDIR}/*
}

do_install() {
    #install -d ${D}/usr/lib/fabmo
    #install -d ${D}/fabmo
    #install -d ${D}/fabmo/site
    #install -d ${D}${systemd_unitdir}/system
    #mv ${S}/node_modules/serialport/build/Release/node-v11-linux-i586 ${S}/node_modules/serialport/build/Release/node-v11-linux-ia32 || true
    #cp -r ${S} ${D}/fabmo/engine
    #install -m 0644 ${S}/files/fabmo.service ${D}${systemd_unitdir}/system/
    #tar -cvjf ${D}/usr/lib/fabmo/engine-factory.tar.bz2 -C ${D}/fabmo/engine --exclude='*install_token' .

    install -d ${D}/usr/lib/fabmo
    install -d ${D}/fabmo
    install -d ${D}/fabmo/engine
    install -d ${D}/fabmo/site
    install -d ${D}${systemd_unitdir}/system
    #mv ${S}/node_modules/serialport/build/Release/node-v11-linux-i586 ${S}/node_modules/serialport/build/Release/node-v11-linux-ia32 || true
	    
    cp -r ${WORKDIR}/fabmo-engine-files/* ${D}/fabmo/engine

    install -m 0644 ${D}/fabmo/engine/files/fabmo.service ${D}${systemd_unitdir}/system/
    tar -cjf ${D}/usr/lib/fabmo/engine-factory.tar.bz2 -C ${D}/fabmo/engine --exclude='*install_token' .
}

inherit systemd

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "fabmo.service"

FILES_${PN} = "${systemd_unitdir}/system /fabmo /opt /usr"

PACKAGES = "${PN}"

INSANE_SKIP_${PN} += "debug-files"
