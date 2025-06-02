package com.application.interfaces;

public interface IConsultationPatientActionsEvent {

    public void onEdit(String patientId);

    public void onDelete(String patientId);

    public void onView(String patientId);
}
