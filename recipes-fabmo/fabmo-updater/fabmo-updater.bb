DESCRIPTION="The FabMo Updater Service"
LICENSE = "Apache-2.0"
 
#SRC_URI = "git://github.com/FabMo/FabMo-Updater.git;protocol=https"
#SRCREV = "${AUTOREV}"
PV = "1.4.49"
DEPENDS = "dbus-glib expat"
RDEPENDS_${PN} = "git bash nodejs-npm bossa factory-reset"

LIC_FILES_CHKSUM = "file://LICENSE;md5=fa818a259cbed7ce8bc2a22d35a464fc"

S = "${WORKDIR}/git"

inherit npm-install

NPM_INSTALL_FLAGS += " --build-from-source"

do_fetch() {
	git clone https://github.com/FabMo/FabMo-Updater.git ${S}
	cd ${S}
	git fetch origin --tags
	git fetch origin release:release
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

do_install() {
    # Install the updater 
    install -d ${D}/fabmo
    cp -r ${S} ${D}/fabmo/updater
    
    # Install the systemd unit file
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${S}/files/fabmo-updater.service ${D}${systemd_unitdir}/system/
    install -m 0644 ${THISDIR}/files/fabmo-updater-update.service ${D}${systemd_unitdir}/system/
    
    # Install the factory backup and required scripts
    install -d ${D}/usr/lib/fabmo
    install -d ${D}${bindir}
    tar -cvjf ${D}/usr/lib/fabmo/updater-factory.tar.bz2 -C ${D}/fabmo/updater --exclude='*install_token' .
    install -m 0755 ${THISDIR}/files/factory_reset.sh ${D}/usr/lib/fabmo/    
    install -m 0755 ${THISDIR}/files/updater_update.sh ${D}/usr/lib/fabmo/    
    install -m 0755 ${THISDIR}/files/enter_ap_mode.sh ${D}/usr/lib/fabmo/    
    install -m 0755 ${THISDIR}/files/fabmo-factory-reset ${D}${bindir}    
    
    # Link the /opt/fabmo configuration path to the home directory (where configs are actually stored)
    install -d ${D}/opt
    install -d ${D}/home/fabmo
    chown -R fabmo ${D}/home/fabmo
    lnr ${D}/home/fabmo ${D}/opt/fabmo

}

do_clean() {
	rm -rf ${S}
}

#pkg_postinst_${PN}() {
#    ln -s ${D}/home/fabmo ${D}/opt/fabmo
#}

inherit systemd

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "fabmo-updater.service"

inherit useradd

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "-d /home/fabmo -r -s /bin/bash fabmo"

FILES_${PN} = "${systemd_unitdir}/system /fabmo /opt /usr /home ${bindir}"

PACKAGES = "${PN}"
