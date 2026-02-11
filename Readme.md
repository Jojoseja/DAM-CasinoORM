# Cambios hechos por Mario (11/02/2026)
- He cambiado el valor cantidadConcepto de double a BigDecimal para que funcione mejor con la BBDD y con Hibernate, 
ya que con double me daba problemas a la hora de escribirlo (por la escala 10,2)
- He creado cosas clientes, servicios y logs para pruebas en el main ya que lo necesitaba para algunso métodos
- Métodos hechos:
  - ganadoEstablecimientos()
  - devolverServiciosTipo()
  - ganadoMesas()
  - vecesClienteJuegaMesa()