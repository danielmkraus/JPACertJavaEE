package com.abank.data.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by: gruppd, 23.03.13 12:46
 */
@Entity
@DiscriminatorValue("QP")
public class QualityProject extends Project {
}
