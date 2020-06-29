// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    Collection<String> attendees = request.getAttendees(); 
    long duration = request.getDuration();  
    ArrayList <TimeRange> unavaliabletimes = new ArrayList <TimeRange>();
    for (Event e: events) {
        Collection <String> busyattendee = e.getAttendees(); 
        for (String busyattendees: busyattendee) {
            for (String meetingattendees: attendees) {
                if (busyattendees == meetingattendees){
                    unavaliabletimes.add(e.getWhen()); 
                }
            }
        }
    } 

    Collections.sort(unavaliabletimes, TimeRange.ORDER_BY_START); 
    ArrayList <TimeRange> freetime = new ArrayList<TimeRange>(); 
    for (int i = 0; i < unavaliabletimes.size(); i++){
        int start1 = (unavaliabletimes.get(i)).end(); 
        int end1 = (unavaliabletimes.get(i+1)).start(); 
        TimeRange timeslot = TimeRange.fromStartEnd(start1, end1, false); 
        int timeslotduration = timeslot.duration(); 
        if (timeslotduration > duration) {
            freetime.add(timeslot);
        }
    }
    return freetime; 
  }
}
