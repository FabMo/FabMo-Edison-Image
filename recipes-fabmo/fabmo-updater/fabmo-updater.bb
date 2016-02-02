DESCRIPTION="The FabMo Updater Service"
LICENSE = "Apache-2.0"
 
#SRC_URI = "git://github.com/FabMo/FabMo-Updater.git;protocol=https"
#SRCREV = "${AUTOREV}"
PV = "1.2.4"
DEPENDS = "dbus-glib expat"
RDEPENDS_${PN} = "git bash nodejs-npm"

LIC_FILES_CHKSUM = "file://LICENSE;md5=fa818a259cbed7ce8bc2a22d35a464fc"

S = "${WORKDIR}/git"

inherit npm

NPM_INSTALL_FLAGS += " --build-from-source"

do_fetch() {
	git clone https://github.com/FabMo/FabMo-Updater.git ${S} --depth=1
}

do_unpack() {
    echo "Unpacking"
}

do_compile() {
    oe_runnpm install
}

do_install() {
    install -d ${D}/opt
    #install -d ${D}/opt/fabmo
    install -d ${D}/fabmo
    install -d ${D}${systemd_unitdir}/system
    #mv ${S}/node_modules/serialport/build/serialport/v1.7.4/Release/node-v11-linux-i586 ${S}/node_modules/serialport/build/serialport/v1.7.4/Release/node-v11-linux-ia32
    cp -r ${S} ${D}/fabmo/updater
    install -m 0644 ${S}/files/fabmo-updater.service ${D}${systemd_unitdir}/system/
    
    #install -d ${D}/opt/fabmo/config
    #echo '{"platform":"edison"}' > ${D}/opt/fabmo/config/updater.json
    
    install -d ${D}/home/fabmo
    chown -R fabmo ${D}/home/fabmo
    lnr ${D}/home/fabmo ${D}/opt/fabmo
}

do_clean() {
	rm -rf ${S}
}

pkg_postinst_${PN}() {
    ln -s ${D}/home/fabmo ${D}/opt/fabmo
}

inherit systemd

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "fabmo-updater.service"

inherit useradd

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "-d /home/fabmo -r -s /bin/bash fabmo"

FILES_${PN} = "${systemd_unitdir}/system /fabmo /opt /usr /home"

PACKAGES = "${PN}"
