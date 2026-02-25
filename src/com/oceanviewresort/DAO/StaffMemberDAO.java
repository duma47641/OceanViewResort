package com.oceanviewresort.DAO;

import com.oceanviewresort.Models.StaffMember;

public interface StaffMemberDAO {

    StaffMember login(String email, String password);

    boolean register(StaffMember admin);

    StaffMember getStaffMemberById(int Id);

    boolean updateStaffMember(StaffMember staffMember);

    boolean isStaffMemberEmailExists(String email);
}