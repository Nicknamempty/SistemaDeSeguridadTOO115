package sistemadeseguridadtoo115

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class UnidadOrganizacionalController {

    UnidadOrganizacionalService unidadOrganizacionalService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured('permitAll')
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond unidadOrganizacionalService.list(params), model:[unidadOrganizacionalCount: unidadOrganizacionalService.count()]
    }

    @Secured('permitAll')
    def show(Long id) {
        respond unidadOrganizacionalService.get(id)
    }

    @Secured('permitAll')
    def create() {
        respond new UnidadOrganizacional(params)
    }

    @Secured('permitAll')
    def save(UnidadOrganizacional unidadOrganizacional) {
        if (unidadOrganizacional == null) {
            notFound()
            return
        }

        try {
            unidadOrganizacionalService.save(unidadOrganizacional)
        } catch (ValidationException e) {
            respond unidadOrganizacional.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'unidadOrganizacional.label', default: 'UnidadOrganizacional'), unidadOrganizacional.id])
                redirect unidadOrganizacional
            }
            '*' { respond unidadOrganizacional, [status: CREATED] }
        }
    }

    @Secured('permitAll')
    def edit(Long id) {
        respond unidadOrganizacionalService.get(id)
    }

    @Secured('permitAll')
    def update(UnidadOrganizacional unidadOrganizacional) {
        if (unidadOrganizacional == null) {
            notFound()
            return
        }

        try {
            unidadOrganizacionalService.save(unidadOrganizacional)
        } catch (ValidationException e) {
            respond unidadOrganizacional.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'unidadOrganizacional.label', default: 'UnidadOrganizacional'), unidadOrganizacional.id])
                redirect unidadOrganizacional
            }
            '*'{ respond unidadOrganizacional, [status: OK] }
        }
    }

    @Secured('permitAll')
    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        unidadOrganizacionalService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'unidadOrganizacional.label', default: 'UnidadOrganizacional'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'unidadOrganizacional.label', default: 'UnidadOrganizacional'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
