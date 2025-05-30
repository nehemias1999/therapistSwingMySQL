package com.application.view.panels.consultation;

import java.awt.event.MouseEvent;

public interface DynamicCellListener {

    public void scrollChanged(boolean scrollNext);

    public void mouseSelected(MouseEvent mouse);
}
