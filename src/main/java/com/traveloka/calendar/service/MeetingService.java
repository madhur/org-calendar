package com.traveloka.calendar.service;


import com.google.gson.JsonArray;
import com.traveloka.calendar.entity.Employee;
import com.traveloka.calendar.entity.Meeting;
import com.traveloka.calendar.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class MeetingService {

    @Autowired
    private EmployeeService employeeService;

    private List<Meeting> meetingList = new ArrayList<>();

    private Map<Employee, List<Meeting>>  employeeListMap = new HashMap<>();

    public Meeting addMeeeting(String meetingName, JsonArray invites, String initiator, LocalDateTime parsedDate) throws UserNotFoundException {

        List<Employee> employeeList = new ArrayList<>();
        Meeting meeting = new Meeting();
        meeting.setAgenda(meetingName);
        meeting.setMeetingId(UUID.randomUUID().toString());
        Employee initiatorEmployee = employeeService.validateEmployee(initiator);
        meeting.setInitiator(initiatorEmployee);
        meeting.setMeetingDate(parsedDate);

        for(int i=0; i<invites.size(); ++i) {
            Employee e  = employeeService.validateEmployee(invites.get(i).getAsString());
            employeeList.add(e);
        }

        meeting.setEmployeeList(employeeList);

        meetingList.add(meeting);

        employeeList.add(initiatorEmployee);
        updateEmployeeMap(meeting, employeeList);

        return meeting;
    }

    public List<Meeting> getMeetingsByEmployee(String employeeId) throws UserNotFoundException {

        Employee e = employeeService.validateEmployee(employeeId);

        return employeeListMap.get(e);
    }

    private void updateEmployeeMap(Meeting m, List<Employee> employees ) {

        for(Employee e: employees) {
            List<Meeting> meetings = employeeListMap.get(e);
            if (meetings == null) {
                meetings = new ArrayList<>();
            }

            meetings.add(m);
            employeeListMap.put(e, meetings);
        }

    }
}
