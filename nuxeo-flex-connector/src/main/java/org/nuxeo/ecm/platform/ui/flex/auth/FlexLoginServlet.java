/*
 * (C) Copyright 2006-2008 Nuxeo SAS (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     Nuxeo - initial API and implementation
 *
 * $Id$
 */

package org.nuxeo.ecm.platform.ui.flex.auth;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nuxeo.ecm.core.api.NuxeoPrincipal;

/**
 * Simple Servlet that is used to return an answer to the Flex client when it authenticates
 * @author Tiry (tdelprat@nuxeo.com)
 *
 */
public class FlexLoginServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        sendLoginResponse(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        sendLoginResponse(req, resp);
    }

    private void sendLoginResponse(HttpServletRequest req,
            HttpServletResponse resp) throws IOException {

        Principal principal = req.getUserPrincipal();
        if (principal != null) {
            NuxeoPrincipal nuxeoPrincipal = (NuxeoPrincipal) principal;

            resp.setContentType("test/xml");

            StringBuffer sb = new StringBuffer();

            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            sb.append("<response>");
            sb.append("<status>OK</status>");
            sb.append("<user>");
            sb.append("<firstName>" + nuxeoPrincipal.getFirstName()
                    + "</firstName>");
            sb.append("<lastName>" + nuxeoPrincipal.getLastName()
                    + "</lastName>");
            sb.append("<name>" + nuxeoPrincipal.getName() + "</name>");
            sb.append("<company>" + nuxeoPrincipal.getCompany() + "</company>");
            sb.append("<groups>");
            for (String group : nuxeoPrincipal.getAllGroups()) {
                sb.append("<group> " + group + "</group>");
            }
            sb.append("</groups>");
            sb.append("</user>");
            sb.append("</response>");

            resp.getWriter().write(sb.toString());
        }
    }
}
