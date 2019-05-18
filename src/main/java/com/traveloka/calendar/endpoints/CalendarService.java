package com.traveloka.calendar.endpoints;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.traveloka.calendar.entity.Employee;
import com.traveloka.calendar.entity.Meeting;
import com.traveloka.calendar.exception.UserNotFoundException;
import com.traveloka.calendar.service.EmployeeService;
import com.traveloka.calendar.service.MeetingService;
import com.traveloka.calendar.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequestMapping("/")
@RestController

public class CalendarService {

    Gson gson = new Gson();

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private RoomService roomService;

    @RequestMapping(value = "/employee" , method = RequestMethod.POST)
    public ResponseEntity<Employee> createeUser(@RequestBody String payload) {
        JsonObject jobj = new Gson().fromJson(payload, JsonObject.class);
        System.out.println(jobj);
        String userName = jobj.get("name").getAsString();
        Employee e = employeeService.addUser(userName);
        return new ResponseEntity<Employee>(e, HttpStatus.OK);
    }

    @RequestMapping(value = "/employee" , method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> getUsers() {
        List<Employee> employeeList = employeeService.getAllEmployees();
        return new ResponseEntity<List<Employee>>(employeeList, HttpStatus.OK);

    }

    @RequestMapping(value = "/meetings/{employeeId}" , method = RequestMethod.GET)
    public ResponseEntity<List<Meeting>> getMeetingsForEmployee(@PathVariable("employeeId") String employeeId) {
        List<Meeting> meetings = null;
        try {
            meetings = meetingService.getMeetingsByEmployee(employeeId);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<List<Meeting>>(meetings, HttpStatus.OK);

    }

    @RequestMapping(value = "/create_meeting" , method = RequestMethod.POST)
    public ResponseEntity<Meeting> createMeeting(@RequestBody String payload) {
        JsonObject jobj = new Gson().fromJson(payload, JsonObject.class);
        String meetingName = jobj.get("name").getAsString();
        JsonArray invites =  jobj.get("invitees").getAsJsonArray();
        String initiator = jobj.get("initiator").getAsString();
        String dateTime = jobj.get("datetime").getAsString();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime parsedDate = LocalDateTime.parse(dateTime, formatter);

        Meeting meeting = null;
        try {
            meeting = meetingService.addMeeeting(meetingName, invites, initiator, parsedDate);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<Meeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);

    }

    @PostConstruct
    public void onStartup() {

        employeeService.addUser("Madhur");
        employeeService.addUser("Amit");
        employeeService.addUser("Sachin");
    }

}
