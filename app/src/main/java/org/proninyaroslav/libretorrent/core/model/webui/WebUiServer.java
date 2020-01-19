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

import android.content.Context;
import android.util.Log;

import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.NanoHTTPD;
import org.nanohttpd.protocols.http.response.Response;
import org.proninyaroslav.libretorrent.core.model.TorrentEngine;
import org.proninyaroslav.libretorrent.core.model.stream.TorrentInputStream;
import org.proninyaroslav.libretorrent.core.model.stream.TorrentStream;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;

import static org.nanohttpd.protocols.http.response.Response.newFixedLengthResponse;
import static org.nanohttpd.protocols.http.response.Status.BAD_REQUEST;
import static org.nanohttpd.protocols.http.response.Status.FORBIDDEN;
import static org.nanohttpd.protocols.http.response.Status.NOT_FOUND;
import static org.nanohttpd.protocols.http.response.Status.NOT_MODIFIED;
import static org.nanohttpd.protocols.http.response.Status.OK;
import static org.nanohttpd.protocols.http.response.Status.PARTIAL_CONTENT;
import static org.nanohttpd.protocols.http.response.Status.RANGE_NOT_SATISFIABLE;

/*
 * The server that allows to stream selected file from a torrent and to which a specific address is assigned.
 * Supports partial content and DLNA (for some file formats)
 */

public class WebUiServer extends NanoHTTPD
{
    @SuppressWarnings("unused")
    private static final String TAG = WebUiServer.class.getSimpleName();

    private static final String MIME_OCTET_STREAM = "application/octet-stream";


    private TorrentEngine engine;

    public WebUiServer(@NonNull String host, int port)
    {
        super(host, port);
    }

    public void start(@NonNull Context appContext) throws IOException
    {
        Log.i(TAG, "Start " + TAG);

        engine = TorrentEngine.getInstance(appContext);

        super.start();
    }

    @Override
    public void stop()
    {
        super.stop();

        Log.i(TAG, "Stop " + TAG);
    }

    @Override
    public Response handle(IHTTPSession session)
    {
        String uri = session.getUri();
        Response res = handleTorrent(session);

        return res;
    }

    public Response handleTorrent(IHTTPSession httpSession)
    {
        String data = "hello world";
        Response res = newFixedLengthResponse(data);
        return res;
    }
}
