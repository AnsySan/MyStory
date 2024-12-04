CREATE INDEX idx_account_user ON account(user_id) WHERE user_id IS NOT NULL;
CREATE INDEX idx_account_project ON account(project_id) WHERE project_id IS NOT NULL;