/*
 * beetlejuice
 * beetlejuice-cdm
 * Copyright (C) 2011-2013 art of coding UG, http://www.art-of-coding.eu
 *
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 *
 * rbe, 11.02.13 13:36
 */

package eu.artofcoding.beetlejuice.cdm.store;

import eu.artofcoding.beetlejuice.cdm.Base;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ReturnLabel extends Base {

    private String identCode;

    private String routingCode;

    private String base64;

    private String filename;

    private transient Path path;

    //<editor-fold desc="Constructor">

    public ReturnLabel(String identCode, String routingCode, String base64) {
        this.identCode = identCode;
        this.routingCode = routingCode;
        this.base64 = base64;
    }

    //</editor-fold>

    //<editor-fold desc="Getter and Setter">

    public String getIdentCode() {
        return identCode;
    }

    public String getRoutingCode() {
        return routingCode;
    }

    public String getBase64() {
        return base64;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Path getPath() {
        return path;
    }

    //</editor-fold>

    public void saveBinary(Path path) throws IOException {
        this.path = path;
        byte[] b = DatatypeConverter.parseBase64Binary(base64);
        if (null == b) {
            throw new IllegalStateException("No bytes");
        } else {
            Path parent = path.getParent();
            if (null != parent) {
                Files.createDirectories(parent);
            }
            Files.write(path, b, StandardOpenOption.CREATE);
        }
    }

}
