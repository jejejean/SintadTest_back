package com.SintadTest.documentType.models.entity;

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
@Builder
@Entity
@Table(name = "tb_tipo_documento")
public class DocumentType implements IHandleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_documento")
    private Long idDocumentType;

    @NotNull(message = "El campo código es requerido")
    @Size(max = 20, message = "El código no puede tener más de 20 caracteres")
    @Column(name = "codigo", unique = true)
    private String code;

    @NotNull(message = "El campo nombre es requerido")
    @Size(min = 3, max = 100, message = "El nombre del documento debe tener entre 3 y 100 caracteres")
    @Column(name = "nombre")
    private String name;

    @Size(max = 200, message = "La descripción no puede tener más de 200 caracteres")
    @Column(name = "descripcion")
    private String description;

    @NotNull(message = "El campo estado es requerido")
    @Column(name = "estado")
    private Boolean state;

    @JsonIgnore
    @OneToMany(mappedBy = "documentType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Entidad> entityList;

}
