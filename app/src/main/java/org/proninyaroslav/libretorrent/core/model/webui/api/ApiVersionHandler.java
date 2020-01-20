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
package org.proninyaroslav.libretorrent.core.model.webui.api;

import org.proninyaroslav.libretorrent.BuildConfig;
import org.proninyaroslav.libretorrent.core.model.webui.ApiHandler;

public class ApiVersionHandler extends ApiHandler {
    class Version {
        int versionCode;
        String versionName;

        public Version(int versionCode, String versionName) {
            this.versionCode = versionCode;
            this.versionName = versionName;
        }
    }

    @Override
    public Object get(ApiParams params) {
        return new Version(BuildConfig.VERSION_CODE, BuildConfig.VERSION_NAME);
    }

}