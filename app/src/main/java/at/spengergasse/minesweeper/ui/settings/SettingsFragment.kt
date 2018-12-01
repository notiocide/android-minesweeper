package at.spengergasse.minesweeper.ui.settings

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Switch
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import at.spengergasse.minesweeper.*
import at.spengergasse.minesweeper.game.GameSettings

class SettingsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.settings_fragment, container, false)

        val prefs = PreferenceManager.getDefaultSharedPreferences(activity)

        val difficultyGroup = view.findViewById<RadioGroup>(R.id.group_difficulty)
        val minesField = view.findViewById<EditText>(R.id.text_mines)
        val rowsField = view.findViewById<EditText>(R.id.text_height)
        val columnsField = view.findViewById<EditText>(R.id.text_width)
        val safeSwitch = view.findViewById<Switch>(R.id.switch_safe)
        val save = view.findViewById<Button>(R.id.button_save)

        difficultyGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio_easy -> {
                    minesField.isEnabled = false
                    rowsField.isEnabled = false
                    columnsField.isEnabled = false
                    save.isEnabled = false

                    columnsField.setText(EASY_COLUMNS.toString())
                    rowsField.setText(EASY_ROWS.toString())
                    minesField.setText(EASY_MINES.toString())

                    prefs.edit {
                        putInt(KEY_PRESET, PRESET_EASY)
                        remove(KEY_ROWS)
                        remove(KEY_COLUMNS)
                        remove(KEY_MINES)
                    }
                }
                R.id.radio_medium -> {
                    minesField.isEnabled = false
                    rowsField.isEnabled = false
                    columnsField.isEnabled = false
                    save.isEnabled = false

                    columnsField.setText(MEDIUM_COLUMNS.toString())
                    rowsField.setText(MEDIUM_ROWS.toString())
                    minesField.setText(MEDIUM_MINES.toString())

                    prefs.edit {
                        putInt(KEY_PRESET, PRESET_MEDIUM)
                        remove(KEY_ROWS)
                        remove(KEY_COLUMNS)
                        remove(KEY_MINES)
                    }
                }
                R.id.radio_hard -> {
                    minesField.isEnabled = false
                    rowsField.isEnabled = false
                    columnsField.isEnabled = false
                    save.isEnabled = false

                    columnsField.setText(HARD_COLUMNS.toString())
                    rowsField.setText(HARD_ROWS.toString())
                    minesField.setText(HARD_MINES.toString())

                    prefs.edit {
                        putInt(KEY_PRESET, PRESET_HARD)
                        remove(KEY_ROWS)
                        remove(KEY_COLUMNS)
                        remove(KEY_MINES)
                    }
                }
                R.id.radio_custom -> {
                    minesField.isEnabled = true
                    rowsField.isEnabled = true
                    columnsField.isEnabled = true
                    save.isEnabled = true
                }
            }
        }

        when (prefs.getInt(KEY_PRESET, -1)) {
            PRESET_EASY -> difficultyGroup.check(R.id.radio_easy)
            PRESET_MEDIUM -> difficultyGroup.check(R.id.radio_medium)
            PRESET_HARD -> difficultyGroup.check(R.id.radio_hard)
            else -> difficultyGroup.check(R.id.radio_custom)
        }

        val loaded = GameSettings.load(prefs)
        minesField.setText(loaded.mines.toString())
        rowsField.setText(loaded.rows.toString())
        columnsField.setText(loaded.columns.toString())
        safeSwitch.isChecked = loaded.safe

        save.setOnClickListener {
            prefs.edit {
                remove(KEY_PRESET)
                putInt(KEY_ROWS, rowsField.text.toString().toInt())
                putInt(KEY_COLUMNS, columnsField.text.toString().toInt())
                putInt(KEY_MINES, minesField.text.toString().toInt())
            }
        }

        safeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            prefs.edit {
                putBoolean(KEY_SAFE, isChecked)
            }
        }

        return view
    }

}