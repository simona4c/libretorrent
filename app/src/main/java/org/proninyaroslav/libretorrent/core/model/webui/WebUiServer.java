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

import org.nanohttpd.router.RouterNanoHTTPD;
import org.proninyaroslav.libretorrent.core.model.TorrentEngine;
import org.proninyaroslav.libretorrent.core.model.webui.api.ApiTorrentListHandler;
import org.proninyaroslav.libretorrent.core.model.webui.api.ApiVersionHandler;

import java.io.IOException;
import androidx.annotation.NonNull;


public class WebUiServer extends RouterNanoHTTPD
{
    @SuppressWarnings("unused")
    private static final String TAG = WebUiServer.class.getSimpleName();

    private static final String MIME_OCTET_STREAM = "application/octet-stream";


    private TorrentEngine engine;

    public WebUiServer(@NonNull String host, int port)
    {
        super(host, port);
    }

    @Override
    public void addMappings() {

        //api
        addRoute("/api/version", ApiVersionHandler.class);
        addRoute("/api/torrent/list", ApiTorrentListHandler.class, engine);
    }

    public void start(@NonNull Context appContext) throws IOException
    {
        Log.i(TAG, "Start " + TAG);

        engine = TorrentEngine.getInstance(appContext);
        addMappings();

        super.start();
    }

    @Override
    public void stop()
    {
        super.stop();

        Log.i(TAG, "Stop " + TAG);
    }
}
