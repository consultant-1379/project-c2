package com.project.RESTEndpoint.Model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="files")
public class FileModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int file_id;
    private String file_name;
    private double hunk_count;
}

