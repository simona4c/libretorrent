/*
 * Copyright (C) 2020 Simon Zajdela <simon.zajdela.a4c@gmail.com>
 *
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

package org.proninyaroslav.libretorrent.ui.settings.sections;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;

import com.takisoft.preferencex.EditTextPreference;
import com.takisoft.preferencex.PreferenceFragmentCompat;

import org.proninyaroslav.libretorrent.R;
import org.proninyaroslav.libretorrent.core.InputFilterRange;
import org.proninyaroslav.libretorrent.core.RepositoryHelper;
import org.proninyaroslav.libretorrent.core.settings.SettingsRepository;

import androidx.preference.Preference;
import androidx.preference.SwitchPreferenceCompat;

public class WebUiSettingsFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceChangeListener
{
    @SuppressWarnings("unused")
    private static final String TAG = WebUiSettingsFragment.class.getSimpleName();

    private SettingsRepository pref;

    public static WebUiSettingsFragment newInstance()
    {
        WebUiSettingsFragment fragment = new WebUiSettingsFragment();
        fragment.setArguments(new Bundle());

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        pref = RepositoryHelper.getSettingsRepository(getActivity().getApplicationContext());

        String keyEnable = getString(R.string.pref_key_webui_enable);
        SwitchPreferenceCompat enable = findPreference(keyEnable);
        if (enable != null) {
            enable.setChecked(pref.enableWebUi());
            bindOnPreferenceChangeListener(enable);
        }

        String keyHostname = getString(R.string.pref_key_webui_hostname);
        EditTextPreference hostname = findPreference(keyHostname);
        if (hostname != null) {
            String addressValue = pref.webUiHostname();
            hostname.setText(addressValue);
            hostname.setSummary(addressValue);
            bindOnPreferenceChangeListener(hostname);
        }

        String keyPort = getString(R.string.pref_key_webui_port);
        EditTextPreference port = findPreference(keyPort);
        if (port != null) {
            InputFilter[] portFilter = new InputFilter[] { InputFilterRange.PORT_FILTER };
            int portNumber = pref.webUiPort();
            String portValue = Integer.toString(portNumber);
            port.setOnBindEditTextListener((editText) -> editText.setFilters(portFilter));
            port.setSummary(portValue);
            port.setText(portValue);
            bindOnPreferenceChangeListener(port);
        }
    }

    @Override
    public void onCreatePreferencesFix(Bundle savedInstanceState, String rootKey)
    {
        setPreferencesFromResource(R.xml.pref_webui, rootKey);
    }

    private void bindOnPreferenceChangeListener(Preference preference)
    {
        preference.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue)
    {
        if (preference.getKey().equals(getString(R.string.pref_key_webui_hostname))) {
            pref.webUiHostname((String)newValue);
            preference.setSummary((String)newValue);

        } else if (preference.getKey().equals(getString(R.string.pref_key_webui_port))) {
            if (!TextUtils.isEmpty((String)newValue)) {
                int value = Integer.parseInt((String) newValue);
                pref.webUiPort(value);
                preference.setSummary(Integer.toString(value));
            }

        } else if (preference.getKey().equals(getString(R.string.pref_key_webui_enable))) {
            pref.enableWebUi((boolean)newValue);
        }

        return true;
    }
}
