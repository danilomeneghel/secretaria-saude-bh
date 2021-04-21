from postgis/postgis:latest

ADD init-postgis-db.sh /docker-entrypoint-initdb.d/init-postgis-db.sh

RUN chmod 777 /docker-entrypoint-initdb.d/init-postgis-db.sh \
	&& sed -i -e 's/\r$//' /docker-entrypoint-initdb.d/init-postgis-db.sh
	
    