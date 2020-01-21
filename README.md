# tap-backendtest

List of guides
[*] Web Layer test : https://spring.io/guides/gs/testing-web/

    //Updates an existing Entity
    public void save(Long id, Entity entity) {

        Optional<Entity> existingEntity = entityRepository.findById(id);
        if (existingEntity.isPresent()) {
            Entity entityToUpdate = existingEntity.get();
            entityToUpdate.setName(entity.getName());
            entityToUpdate.setModifiedDateTime(LocalDateTime.now());
            log.info("Update Entity in store: name = " + entity.getName());
            entityRepository.save(entityToUpdate);
        }
        else {
            log.severe("Entity not found: id = " + id);
            throw new EntityNotFoundException("id - " + id);
        }
    }
