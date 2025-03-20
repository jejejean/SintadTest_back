package com.SintadTest.entity.models.entity;

import com.SintadTest.documentType.models.entity.DocumentType;
import com.SintadTest.shared.interfaces.IHandleEntity;
import com.SintadTest.taxpayerType.models.entity.TaxpayerType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_entidad")
public class Entidad implements IHandleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entidad")
    private Long idEntity;

    @Size(max = 50, message = "El campo nombre debe tener como máximo 50 caracteres")
    @NotNull(message = "El campo num documento es requerido")
    @Column(name = "nro_documento", unique = true)
    private String numDocument;

    @Size(min = 3, max = 100, message = "El campo razon social debe tener entre 3 y 100 caracteres")
    @NotNull(message = "El campo razon social es requerido")
    @Column(name = "razon_social")
    private String companyName;

    @Size(max = 100, message = "El campo nombre comercial debe tener como máximo 100 caracteres")
    @Column(name = "nombre_comercial")
    private String tradeName;

    @Size(min = 3, max = 100, message = "El campo direccion debe tener entre 3 y 100 caracteres")
    @NotNull(message = "El campo direccion es requerido")
    @Column(name = "direccion")
    private String address;

    @Size(max = 50, message = "El campo telefono debe tener como máximo 50 caracteres")
    @NotNull(message = "El campo telefono es requerido")
    @Column(name = "telefono")
    private String phone;

    @NotNull(message = "El campo correo es requerido")
    @Column(name = "estado")
    private Boolean state;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo_contribuyente")
    @JsonBackReference
    private TaxpayerType taxpayerType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo_documento")
    @JsonBackReference
    private DocumentType documentType;
}
