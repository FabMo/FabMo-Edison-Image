DESCRIPTION="The FabMo Engine Service"
LICENSE = "Apache-2.0"
 
#SRC_URI = "git://github.com/Fabmo/FabMo-Engine.git;protocol=https"
#SRCREV = "${AUTOREV}"
PV = "1.4.50"

DEPENDS = "dbus-glib expat fabmo-updater"
RDEPENDS_${PN} = "git bash nodejs-npm"

LIC_FILES_CHKSUM = "file://LICENSE;md5=175792518e4ac015ab6696d16c4f607e"

S = "${WORKDIR}/git"

inherit npm-base

NPM_INSTALL_FLAGS += " --build-from-source"

do_fetch() {
	git clone https://github.com/FabMo/FabMo-Engine.git ${S}
	cd ${S}
	git fetch origin --tags
	git fetch origin release:release
	git fetch origin rc:rc
	git checkout release
	VERSION=`git describe`
	echo "{\"number\" : \"$VERSION\" }" > version.json
	rm -rf .git
        touch install_token
}

do_unpack() {
	echo "Unpacking"
}

do_compile() {
    oe_runnpm install
}

do_clean() {
	rm -rf ${S}
}

do_install() {
    install -d ${D}/usr/lib/fabmo
    install -d ${D}/fabmo
    install -d ${D}${systemd_unitdir}/system
    mv ${S}/node_modules/serialport/build/Release/node-v11-linux-i586 ${S}/node_modules/serialport/build/Release/node-v11-linux-ia32 || true
    cp -r ${S} ${D}/fabmo/engine
    install -m 0644 ${S}/files/fabmo.service ${D}${systemd_unitdir}/system/
    tar -cvjf ${D}/usr/lib/fabmo/engine-factory.tar.bz2 -C ${D}/fabmo/engine --exclude='*install_token' .
}

inherit systemd

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "fabmo.service"

FILES_${PN} = "${systemd_unitdir}/system /fabmo /opt /usr"

PACKAGES = "${PN}"
