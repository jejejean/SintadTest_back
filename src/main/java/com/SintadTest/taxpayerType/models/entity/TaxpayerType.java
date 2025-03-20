package com.SintadTest.taxpayerType.models.entity;

import com.SintadTest.entity.models.entity.Entidad;
import com.SintadTest.shared.interfaces.IHandleEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "tb_tipo_contribuyente")
public class TaxpayerType implements IHandleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_contribuyente")
    private Long idTaxpayerType;

    @NotNull(message = "El campo nombre es requerido")
    @Size(min = 3, max = 50, message = "El campo nombre debe tener entre 3 y 50 caracteres")
    @Column(name = "nombre")
    private String name;

    @NotNull(message = "El campo estado es requerido")
    @Column(name = "estado")
    private Boolean state;

    @JsonIgnore
    @OneToMany(mappedBy = "taxpayerType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Entidad> entityList;

}
