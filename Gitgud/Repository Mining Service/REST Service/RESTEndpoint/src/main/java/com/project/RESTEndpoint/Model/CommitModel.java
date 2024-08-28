package com.project.RESTEndpoint.Model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="commits")
public class CommitModel implements Serializable {
    @Id
    private String commit_hash_id;
    private int insertions;
    private int deletions;

    private Date date_submitted;
    private String contributor_email;
    private int files_changed;
}
