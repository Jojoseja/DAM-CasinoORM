# Cambios hechos por Mario (11/02/2026)
- He cambiado el valor cantidadConcepto de double a BigDecimal para que funcione mejor con la BBDD y con Hibernate, 
ya que con double me daba problemas a la hora de escribirlo (por la escala 10,2)
- He creado cosas clientes, servicios y logs para pruebas en el main ya que lo necesitaba para algunso métodos
- Métodos hechos:
  - ganadoEstablecimientos()
  - devolverServiciosTipo()
  - ganadoMesas()
  - vecesClienteJuegaMesa()

# Cambios hecho por Jose (16/02/2026)
- He cambiado el valor return de una funcion de float a BigDecimal
- He creado un "pruebas" para hacer _pruebas_ y no tocar el main
- Metodos hechos:
  - leerListaServicios()
  - leerListaClientes()
  - gananciasAlimentos()
  - consultaLog()

# Ultimos Cambios
- Modificadas los metodos para utilizar apropiadamente las excepciones