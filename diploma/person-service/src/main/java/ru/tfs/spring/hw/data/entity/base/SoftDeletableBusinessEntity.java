package ru.tfs.spring.hw.data.entity.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class SoftDeletableBusinessEntity extends AuditedBusinessEntity {

    @Column(name = "is_deleted")
    private boolean deleted;
}
