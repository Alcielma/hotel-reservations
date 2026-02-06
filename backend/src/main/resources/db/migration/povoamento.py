from faker import Faker
import random
from datetime import datetime, timedelta

fake = Faker(['pt_BR'])

file_out = open("V2__povoamento.sql", "w", encoding="utf-8")

def gerar_povoamento():
    file_out.write("INSERT INTO hotel (nome) VALUES ('Horizon');\n")
    file_out.write("INSERT INTO endereco (hotel_id, cep, rua, numero, bairro, cidade, estado) VALUES (1, '55290-000', 'Av Rui Barbosa', '50', 'Heliopolis', 'Garanhuns', 'PE');\n")
    file_out.write("INSERT INTO email_hotel (hotel_id, email) VALUES (1, 'contato@horizon.com.br');\n")
    file_out.write("INSERT INTO telefone_hotel (hotel_id, telefone) VALUES (1, '(87) 3762-0000');\n")

    for i in range(1, 5):
        file_out.write(f"INSERT INTO categoria (preco_diaria, capacidade) VALUES ({100.0 * i}, {i});\n")
    
    comodidades = ['Wi-Fi', 'Ar Condicionado', 'Frigobar', 'Smart TV', 'Banheira']
    for nome in comodidades:
        file_out.write(f"INSERT INTO comodidade (nome) VALUES ('{nome}');\n")

    for i in range(1, 21):
        file_out.write(f"INSERT INTO quarto (hotel_id, categoria_id, numero, status, area) VALUES (1, {random.randint(1,3)}, '{100+i}', 'Disponível', {20.0+i});\n")

    cpfs = []
    for i in range(15):
        cpf = fake.cpf().replace('.','').replace('-','')
        cpfs.append(cpf)
        file_out.write(f"INSERT INTO cliente (cpf, nome, data_nascimento, email, telefone) VALUES ('{cpf}', '{fake.name()}', '{fake.date_of_birth(minimum_age=18)}', '{fake.email()}', '{fake.cellphone_number()}');\n")

    data_hoje = datetime.now().date()
    file_out.write(f"INSERT INTO reserva (quarto_id, cpf_cliente, data_checkin_previsto, data_checkout_previsto, status_reserva) "
                   f"VALUES (1, '{cpfs[0]}', '{data_hoje}', '{data_hoje + timedelta(days=2)}', 'Concluída');\n")
    
    file_out.write(f"INSERT INTO hospedagem (reserva_id, quarto_id, cpf_cliente, data_checkin_real) "
                   f"VALUES (1, 1, '{cpfs[0]}', now());\n")
    
    file_out.write(f"INSERT INTO pagamento (hospedagem_id, valor_total, metodo_pagamento, status_pagamento) "
                   f"VALUES (1, 250.00, 'Pix', 'Concluído');\n")

    file_out.close()
    print("Arquivo V2__povoamento.sql gerado com sucesso!")

gerar_povoamento()