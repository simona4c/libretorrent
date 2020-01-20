/*
 * Copyright (C) 2020 Simon Zajdela <simon.zajdela.a4c@gmail.com>
 * This file is part of LibreTorrent.
 *
 * LibreTorrent is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LibreTorrent is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with LibreTorrent.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.proninyaroslav.libretorrent.core.model.webui;

import com.google.gson.Gson;

import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.response.Response;
import org.nanohttpd.protocols.http.response.Status;
import org.nanohttpd.router.RouterNanoHTTPD;
import java.util.Map;

public abstract class ApiHandler implements RouterNanoHTTPD.UriResponder {
    public class ApiParams {
        private RouterNanoHTTPD.UriResource uriResource;
        private Map<String, String> urlParams;
        private IHTTPSession session;

        public RouterNanoHTTPD.UriResource getUriResource() {
            return uriResource;
        }

        public Map<String, String> getUrlParams() {
            return urlParams;
        }

        public IHTTPSession getSession() {
            return session;
        }

        public ApiParams(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, IHTTPSession session) {
            this.uriResource = uriResource;
            this.urlParams = urlParams;
            this.session = session;
        }
    }
    public String getMimeType() {
        return "application/json";
    }

    public Object get(ApiParams params) {
        return Response.newFixedLengthResponse(Status.NOT_FOUND, getMimeType(), "Not Found");
    }

    private Response serve(Object obj) {
        if (obj instanceof  Response) {
            return (Response) obj;
        } else {
            Gson gson = new Gson();
            return Response.newFixedLengthResponse(Status.OK, getMimeType(), gson.toJson(obj));
        }
    }

    public Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, IHTTPSession session) {
        return serve(get(new ApiParams(uriResource, urlParams, session)));
    }

    public Response post(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, IHTTPSession session) {
        return Response.newFixedLengthResponse(Status.NOT_FOUND, getMimeType(), "Not Found");
    }

    public Response put(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, IHTTPSession session) {
        return Response.newFixedLengthResponse(Status.NOT_FOUND, getMimeType(), "Not Found");
    }

    public Response delete(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, IHTTPSession session) {
        return Response.newFixedLengthResponse(Status.NOT_FOUND, getMimeType(), "Not Found");
    }

    public Response other(String method, RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, IHTTPSession session) {
        return Response.newFixedLengthResponse(Status.NOT_FOUND, getMimeType(), "Not Found");
    }
}