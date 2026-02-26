import { BrowserRouter, Routes, Route } from 'react-router-dom';
import MainLayout from './components/layout/layout';
import Home from './pages/HomePage';
import MinhasReservasPage from './pages/MinhasReservasPage';
import LoginPage from './pages/LoginPage';
import CadastroPage from './pages/CadastroPage';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/cadastro" element={<CadastroPage />} />
        
        <Route path="/home" element={<MainLayout />}>
          
          <Route index element={<Home />} />
          
          <Route path="cadastro" element={<CadastroPage />} />

          <Route path="reservas" element={<MinhasReservasPage />} />
          
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;