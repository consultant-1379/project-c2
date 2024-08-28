package com.project.RESTEndpoint.Model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="contributors")
public class ContributorModel implements Serializable {
    @Id
    private String contributor_email;
    private String contributor_name;
}