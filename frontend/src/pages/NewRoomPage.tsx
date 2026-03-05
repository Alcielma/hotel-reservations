import React, { useState, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './NewRoomPage.module.css';
import toast from 'react-hot-toast';
import CategoryModal from '../components/room/CategoryModal';

interface Item {
  id: number;
  nome: string;
}

interface Categoria {
    precoDiaria: number;
    nome: string;
    capacidade: number;
}

const NewRoomPage: React.FC = () => {
  const navigate = useNavigate();
  const editInputRef = useRef<HTMLInputElement>(null);
  
  // Mocks de dados (Esses viriam do useEffect buscando da sua API)
  const [categorias, setCategorias] = useState<Item[]>([
    { id: 1, nome: 'Standard Solo' },
    { id: 2, nome: 'Double Deluxe' }
  ]);
  const [comodidades, setComodidades] = useState<Item[]>([
    { id: 1, nome: 'Wi-Fi' },
    { id: 2, nome: 'Ar Condicionado' },
    { id: 3, nome: 'Frigobar' }
  ]);

  // Estados de Controle
  const [selectedComods, setSelectedComods] = useState<number[]>([]);
  const [isAddingComod, setIsAddingComod] = useState(false);
  const [isAddingCat, setIsAddingCat] = useState(false);
  const [editingItem, setEditingItem] = useState<{tipo: 'cat' | 'com', item: Item} | null>(null);

  const handleAddQuick = (nome: string, tipo: 'cat' | 'com') => {
    if (!nome.trim()) return;
    const novoItem = { id: Date.now(), nome }; 
    
    if (tipo === 'cat') setCategorias([...categorias, novoItem]);
    else setComodidades([...comodidades, novoItem]);
    
    toast.success(`${tipo === 'cat' ? 'Categoria' : 'Comodidade'} criada!`);
  };

  const handleUpdate = () => {
    const novoNome = editInputRef.current?.value;
    if (!editingItem || !novoNome) return;

    if (editingItem.tipo === 'cat') {
      setCategorias(prev => prev.map(i => i.id === editingItem.item.id ? { ...i, nome: novoNome } : i));
    } else {
      setComodidades(prev => prev.map(i => i.id === editingItem.item.id ? { ...i, nome: novoNome } : i));
    }
    
    setEditingItem(null);
    toast.success("Nome atualizado!");
  };

  const handleDeleteWithConfirm = (id: number, tipo: 'cat' | 'com') => {
    toast((t) => (
      <span className={styles.toastConfirm}>
        Excluir este item?
        <button 
          onClick={() => {
            if (tipo === 'cat') setCategorias(prev => prev.filter(i => i.id !== id));
            else setComodidades(prev => prev.filter(i => i.id !== id));
            toast.dismiss(t.id);
            toast.error("Item removido");
          }}
          className={styles.toastBtnSim}
        > Sim </button>
        <button onClick={() => toast.dismiss(t.id)} className={styles.toastBtnNao}> Não </button>
      </span>
    ), { duration: 4000 });
  };

  const toggleComod = (id: number) => {
    setSelectedComods(prev => prev.includes(id) ? prev.filter(i => i !== id) : [...prev, id]);
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    
    const payload = {
      numero: formData.get('numero'),
      area: formData.get('area'),
      status: "DISPONIVEL", 
      categoria: { id: Number(formData.get('categoria_id')) },
      comodidades: selectedComods.map(id => ({ id })) 
    };

    if (!payload.categoria.id || !payload.numero) {
      toast.error("Preencha o número e a categoria!");
      return;
    }

    console.log("Enviando para o Backend:", payload);
    toast.success("Quarto cadastrado com sucesso!");
    navigate('/quartos');
  };

  const handleSaveCategory = async (novaCat: Categoria) => {
    try {
        console.log("Enviando Categoria ao Backend:", novaCat);
        
        const categoriaSalva = { ...novaCat, id: Date.now() }; 
        setCategorias([...categorias, categoriaSalva]);
        
        setIsAddingCat(false);
        toast.success("Categoria cadastrada com sucesso!");
    } catch (error) {
        console.error("Erro ao salvar categoria: ", error);
        toast.error("Erro ao salvar categoria.");
    }
    };

  return (
    <div className={styles.container}>
      <header className={styles.header}>
        <button className={styles.backBtn} onClick={() => navigate('/quartos')}>
          <span className={styles.iconArrow}>←</span>
          <span>Voltar</span>
        </button>
        <h1>Nova Acomodação</h1>
      </header>

      <form className={styles.mainForm} onSubmit={handleSubmit}>
        <div className={styles.row}>
          <div className={styles.inputGroup}>
            <label>Número do Quarto</label>
            <input name="numero" type="text" placeholder="Ex: 101-A" required />
          </div>
          <div className={styles.inputGroup}>
            <label>Área (m²)</label>
            <input name="area" type="number" step="0.01" placeholder="0.00" />
          </div>
        </div>

        {/* CATEGORIA */}
        <div className={styles.inputGroup}>
          <div className={styles.labelWithAction}>
            <label>Categoria</label>
            <button 
              type="button" 
              className={styles.textActionBtn} 
              onClick={() => setIsAddingCat(true)}
            > + Nova Categoria </button>
          </div>
          
          <div className={styles.selectWithEdit}>
            <select name="categoria_id" className={styles.mainSelect} required>
              <option value="">Selecione uma categoria...</option>
              {categorias.map(cat => (
                <option key={cat.id} value={cat.id}>{cat.nome}</option>
              ))}
            </select>
            
            <div className={styles.quickList}>
               {categorias.slice(-3).map(cat => (
                 <span key={cat.id} className={styles.miniLink} onClick={() => setEditingItem({tipo: 'cat', item: cat})}>
                   Editar {cat.nome}
                 </span>
               ))}
            </div>
          </div>
        </div>

        {/* COMODIDADES */}
        <div className={styles.inputGroup}>
          <label>Comodidades (Selecione as incluídas)</label>
          <div className={styles.chipsGrid}>
            {comodidades.map(com => (
              <div 
                key={com.id} 
                className={`${styles.chipWrapper} ${selectedComods.includes(com.id) ? styles.chipSelected : ''}`}
              >
                <span className={styles.chipText} onClick={() => toggleComod(com.id)}>
                  {com.nome}
                </span>
                <div className={styles.chipActions}>
                  <button type="button" onClick={() => setEditingItem({tipo: 'com', item: com})}>✎</button>
                  <button type="button" className={styles.del} onClick={() => handleDeleteWithConfirm(com.id, 'com')}>×</button>
                </div>
              </div>
            ))}
            
            {isAddingComod ? (
              <input 
                className={styles.inlineInput} 
                autoFocus 
                onKeyDown={(e) => {
                  if (e.key === 'Enter') {
                    handleAddQuick(e.currentTarget.value, 'com');
                    setIsAddingComod(false);
                  }
                }}
                onBlur={() => setIsAddingComod(false)}
                placeholder="Nome..."
              />
            ) : (
              <button type="button" className={styles.addChipBtn} onClick={() => setIsAddingComod(true)}>
                + Comodidade
              </button>
            )}
          </div>
        </div>

        <div className={styles.formFooter}>
          <button type="submit" className={styles.saveBtn}>Finalizar Cadastro</button>
        </div>
      </form>

      {editingItem && (
        <div className={styles.overlay} onClick={() => setEditingItem(null)}>
          <div className={styles.miniDialog} onClick={e => e.stopPropagation()}>
            <p>Alterar nome de {editingItem.tipo === 'cat' ? 'Categoria' : 'Comodidade'}</p>
            <input 
              ref={editInputRef} 
              autoFocus 
              defaultValue={editingItem.item.nome}
              onKeyDown={(e) => e.key === 'Enter' && handleUpdate()}
            />
            <div className={styles.dialogBtns}>
              <button type="button" onClick={() => setEditingItem(null)}>Cancelar</button>
              <button type="button" className={styles.confirmBtn} onClick={handleUpdate}>Salvar</button>
            </div>
          </div>
        </div>
      )}
      
      {isAddingCat && (
        <CategoryModal 
            onClose={() => setIsAddingCat(false)} 
            onSave={handleSaveCategory} 
        />
        )}
    </div>
  );
};

export default NewRoomPage;