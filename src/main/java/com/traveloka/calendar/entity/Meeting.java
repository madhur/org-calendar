package com.traveloka.calendar.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Meeting {

    private String meetingId;

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomNamee(String roomNamee) {
        this.roomName = roomNamee;
    }

    private String roomName;

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public Employee getInitiator() {
        return initiator;
    }

    public void setInitiator(Employee initiator) {
        this.initiator = initiator;
    }

    private String agenda;

    private List<Employee> employeeList;

    private Employee initiator;

    public LocalDateTime getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(LocalDateTime meetingDate) {
        this.meetingDate = meetingDate;
    }

    private LocalDateTime meetingDate;
}
