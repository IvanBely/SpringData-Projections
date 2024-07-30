package com.example.SpringData_Projections.projection;

import lombok.Getter;
import lombok.Setter;

public interface EmployeeProjection {
    String getFullName();
    String getPosition();
    String getDepartmentName();

}
