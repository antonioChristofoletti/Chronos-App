package chronos.chronos.Geral;

import android.text.Editable;
import android.text.TextWatcher;

public class MaskWatcher implements TextWatcher {

    public static final String FORMAT_CPF = "###.###.###-##";
    public static final String FORMAT_RG = "##.###.###.##";
    public static final String FORMAT_FONE = "(###)#####-####";
    public static final String FORMAT_CEP = "#####-###";
    public static final String FORMAT_DATE = "##/##/#### ##:##";
    public static final String FORMAT_HOUR = "##:##";

    private boolean isRunning = false;
    private boolean isDeleting = false;
    private final String mask;

    public MaskWatcher(String mask) {
        this.mask = mask;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        isDeleting = count > after;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

        if(Geral.removerMascara("" + charSequence, mask).equals(""))
        {
            return;
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

        try {
            if (isRunning || isDeleting) {
                return;
            }
            isRunning = true;

            int editableLength = editable.length();
            if (editableLength < mask.length()) {
                if (mask.charAt(editableLength) != '#') {
                    editable.append(mask.charAt(editableLength));
                } else if (mask.charAt(editableLength - 1) != '#') {
                    editable.insert(editableLength - 1, mask, editableLength - 1, editableLength);
                }
            }

        } catch (Exception ex) {
        }
        isRunning = false;
    }

}