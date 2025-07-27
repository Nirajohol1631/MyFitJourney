package com.cdac.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDetailsRequest {
    private Long userId;
    private Long membershipPlanId;
    private Long assignedTrainerId; // Nullable
}