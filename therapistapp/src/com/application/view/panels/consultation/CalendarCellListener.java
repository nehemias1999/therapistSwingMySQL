package com.application.view.panels.consultation;

import java.awt.event.MouseEvent;

public interface CalendarCellListener {

    public void cellSelected(MouseEvent evet, int index);

    public void scrollChanged();
}
